package com.huhusw.serialize.hessian;


import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.huhusw.exception.SerializeException;
import com.huhusw.serialize.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Hessian是一个动态类型的、二进制的序列化和网络服务协议，为面向对象的传输而设计。
 */
public class HessianSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            HessianOutput hessianOutput = new HessianOutput(byteArrayOutputStream);
            hessianOutput.writeObject(obj);

            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new SerializeException("Serialization failed");
        }

    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)) {
            HessianInput hessianInput = new HessianInput(byteArrayInputStream);
            Object o = hessianInput.readObject();

            return clazz.cast(o);

        } catch (Exception e) {
            throw new SerializeException("Deserialization failed");
        }

    }
}
