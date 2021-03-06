/*
 * Copyright 2015 Anton Tananaev (anton.tananaev@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.traccar;

import com.ning.http.client.AsyncHttpClient;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;
import java.util.TimeZone;
import org.traccar.helper.Crc;
import org.traccar.model.Device;
import org.traccar.model.Position;

public class WebDataHandler extends BaseDataHandler {

    private final String url;

    public WebDataHandler(String url) {
        this.url = url;
    }
    
    private static String formatSentence(Position position) {

        StringBuilder s = new StringBuilder("$GPRMC,");

        try (Formatter f = new Formatter(s, Locale.ENGLISH)) {

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.ENGLISH);
            calendar.setTimeInMillis(position.getFixTime().getTime());
            
            f.format("%1$tH%1$tM%1$tS.%1$tL,A,", calendar);
            
            double lat = position.getLatitude();
            double lon = position.getLongitude();
            f.format("%02d%07.4f,%c,", (int) Math.abs(lat), Math.abs(lat) % 1 * 60, lat < 0 ? 'S' : 'N');
            f.format("%03d%07.4f,%c,", (int) Math.abs(lon), Math.abs(lon) % 1 * 60, lon < 0 ? 'W' : 'E');
            
            f.format("%.2f,%.2f,", position.getSpeed(), position.getCourse());
            f.format("%1$td%1$tm%1$ty,,", calendar);
        }

        s.append(Crc.nmeaChecksum(s.toString()));

        return s.toString();
    }

    @Override
    protected Position handlePosition(Position position) {
        
        Device device = Context.getIdentityManager().getDeviceById(position.getDeviceId());
        
        String request = url.
                replaceAll("\\{uniqueId}", device.getUniqueId()).
                replaceAll("\\{deviceId}", String.valueOf(device.getId())).
                replaceAll("\\{fixTime}", String.valueOf(position.getFixTime().getTime())).
                replaceAll("\\{latitude}", String.valueOf(position.getLatitude())).
                replaceAll("\\{longitude}", String.valueOf(position.getLongitude())).
                replaceAll("\\{gprmc}", formatSentence(position));
        
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.prepareGet(request).execute();

        return position;
    }

}
