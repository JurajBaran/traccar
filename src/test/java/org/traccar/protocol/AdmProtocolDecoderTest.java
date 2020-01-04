package org.traccar.protocol;

import org.junit.Test;
import org.traccar.ProtocolTest;

public class AdmProtocolDecoderTest extends ProtocolTest {

    @Test
    public void testDecode() throws Exception {

        AdmProtocolDecoder decoder = new AdmProtocolDecoder(null);

        verifyNull(decoder, binary(
                "010042033836313331313030323639343838320501000000000000000000000000000000000000000000000000000000000000000000000000000000000000000073"));

        verifyPosition(decoder, binary(
                "01002e40041c0744009dfe6742c6c860427402000000f4ff077752c8f55b000000000b4132010213430100041e"));

        verifyPosition(decoder, binary(
                "01002680336510002062A34C423DCF8E42A50B1700005801140767E30F568F2534107D220000"));

        verifyPosition(decoder, binary(
                "010022003300072020000000000000000044062A330000000000107F10565D4A8310"));

        verifyPosition(decoder, binary(
                "0100268033641080207AA34C424CCF8E4239030800005B01140755E30F560000F00F70220000"));

        verifyPosition(decoder, binary(
                "01002680336510002062A34C423DCF8E42A50B1700005801140767E30F568F2534107D220000"));

        verifyPosition(decoder, binary(
                "01002200333508202000000000000000007F0D9F030000000000E39A1056E24A8210"));

        verifyNotNull(decoder, binary(
                "01008449443d3120536f66743d30783531204750533d313036382054696d653d30383a35393a32302031302e30392e31372056616c3d30204c61743d36312e36373738204c6f6e3d35302e3832343520563d3020536174436e743d342b3720537461743d30783030313020496e5f616c61726d3d30783030000000000000000000000000"));
    }

}
