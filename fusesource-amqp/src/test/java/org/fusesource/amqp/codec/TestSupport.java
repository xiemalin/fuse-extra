/**
 * Copyright (C) 2012 FuseSource Corp. All rights reserved.
 * http://fusesource.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.fusesource.amqp.codec;

import org.fusesource.amqp.types.*;
import org.fusesource.amqp.codec.marshaller.TypeReader;
import org.fusesource.amqp.types.Envelope;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.DataByteArrayInputStream;
import org.fusesource.hawtbuf.DataByteArrayOutputStream;

import static org.fusesource.amqp.types.MessageSupport.decodeEnvelope;

/**
 *
 */
public class TestSupport {

    private static final int MAX_SIZE = 80;

    public static AMQPTransportFrame writeRead(AMQPTransportFrame in, boolean display) throws Exception {
        long size = in.getFrameSize();
        if ( size < MAX_SIZE && display ) {
            System.out.printf("\n%s", in);
        }
        Buffer buf = write(in);
        if ( size < MAX_SIZE && display ) {
            System.out.printf("\n%s", string(buf.data));
        }
        AMQPTransportFrame out = read(buf);
        if ( size < MAX_SIZE && display ) {
            System.out.printf("\n%s\n", out);
        }
        return out;
    }

    public static Buffer write(AMQPTransportFrame in) throws Exception {
        DataByteArrayOutputStream out = new DataByteArrayOutputStream((int) in.getFrameSize());
        in.write(out);
        return out.toBuffer();
    }

    public static AMQPTransportFrame read(Buffer buf) throws Exception {
        DataByteArrayInputStream in = new DataByteArrayInputStream(buf);
        return new AMQPTransportFrame(in);
    }

    public static <T extends AMQPType> byte[] write(T value) throws Exception {
        DataByteArrayOutputStream out = new DataByteArrayOutputStream((int) value.size());
        value.write(out);
        return out.getData();
    }

    public static <T extends AMQPType> T read(byte[] b) throws Exception {
        DataByteArrayInputStream in = new DataByteArrayInputStream(b);
        return (T) TypeReader.read(in);
    }

    public static <T extends AMQPType> T writeRead(T value, boolean display) throws Exception {
        long size = value.size();
        if ( size < MAX_SIZE && display ) {
            System.out.printf("%s -> ", value);
        }
        byte b[] = write(value);
        if ( size < MAX_SIZE && display ) {
            System.out.printf("%s -> ", string(b));
        }
        T rc = (T) read(b);
        if ( size < MAX_SIZE && display ) {
            System.out.printf("%s\n", rc);
        }
        return rc;
    }

    public static <T extends AMQPType> T writeRead(T value) throws Exception {
        return writeRead(value, true);
    }

    public static String string(byte[] b) {
        String rc = "length=" + b.length + " : ";
        for ( byte l : b ) {
            rc += String.format("[0x%x]", l);
        }
        return rc;
    }

    static Envelope encodeDecode(Envelope in) throws Exception {
        return encodeDecode(in, true);
    }

    static Envelope encodeDecode(Envelope in, boolean display) throws Exception {
        long size = ((Envelope) in).size();

        if ( size < MAX_SIZE && display ) {
            System.out.printf("\n%s", in);
        }

        Buffer encoded = MessageSupport.toBuffer(in);
        if ( size < MAX_SIZE && display ) {
            System.out.printf("\n%s", string(encoded.data));
        }

        Envelope out = decodeEnvelope(encoded);
        if ( size < MAX_SIZE && display ) {
            System.out.printf("\n%s\n", out);
        }
        return out;
    }
    public static AMQPTransportFrame encodeDecode(AMQPTransportFrame in) throws Exception {
        return encodeDecode(in, true);
    }

    public static AMQPTransportFrame encodeDecode(AMQPTransportFrame in, boolean display) throws Exception {
        long size = in.getSize();
        if ( size < MAX_SIZE && display ) {
            System.out.printf("\n%s", in);
        }
        DataByteArrayOutputStream out = new DataByteArrayOutputStream((int)in.getSize());
        in.write(out);

        Buffer encoded = out.toBuffer();

        if ( size < MAX_SIZE && display ) {
            System.out.printf("\n%s", string(encoded.getData()));
        }

        AMQPTransportFrame rc = new AMQPTransportFrame(new DataByteArrayInputStream(encoded));
        if ( size < MAX_SIZE && display ) {
            System.out.printf("\n%s\n", rc);
        }

        return rc;
    }
}
