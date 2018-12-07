package top.myjnxj.lotterycore.po;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @ClassName MyRedisSerializer
 * @Description TODO
 * @Author 江南小俊
 * @Date 2018/11/4 20:25
 * @Version 1.0.0
 **/
public class MyRedisSerializer implements RedisSerializer<Object> {
    private Converter<Object, byte[]> serializer = new SerializingConverter();
    private Converter<byte[], Object> deserializer = new DeserializingConverter();
    static final byte[] EMPTY_ARRAY = new byte[0];

    @Override
    public byte[] serialize(Object o) {
        if (o == null) {
            return EMPTY_ARRAY;
        }
        try {
            return serializer.convert(o);
        } catch (Exception e) {
            return EMPTY_ARRAY;
        }
    }

    @Override
    public Object deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        } else {
            return deserializer.convert(bytes);
        }
    }
}
