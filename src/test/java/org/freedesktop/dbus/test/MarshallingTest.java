package org.freedesktop.dbus.test;

import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.messages.Message;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Byte.parseByte;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MarshallingTest {

    private static final String SIGNATURE = "a(oa{sv})ao";

    private static byte[] content;

    @BeforeAll
    public static void init() {
        Scanner s = new Scanner(MarshallingTest.class.getResourceAsStream("/content.txt")).useDelimiter(", ");
        List<Byte> byteList = new ArrayList<>();
        while (s.hasNext()) {
            byteList.add(parseByte(s.next()));
        }
        content = new byte[byteList.size()];
        for (int idx = 0; idx < byteList.size(); idx++) {
            content[idx] = byteList.get(idx);
        }
    }

    @Test
    public void testMarshalling() throws DBusException {
        Message msg = new Message((byte) 'l', (byte) 4, (byte) 0) {
        };

        Object[] params = msg.extract(SIGNATURE, content, 0);

        assertTrue(params[0] instanceof List, "First param is not a List");
        assertTrue(params[1] instanceof List, "Second param is not a List");
    }
}
