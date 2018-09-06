/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月2日 下午2:14:10 
 * @version V1.0
 */
package test.edianniu.cs.http.okhttp;

import java.io.IOException;

import com.alibaba.fastjson.JSON;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月2日 下午2:14:10 
 * @version V1.0
 */
public class FastJsonRequestBodyConverter <T> implements Converter<T, RequestBody>{
	private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    @Override
    public RequestBody convert(T value) throws IOException {
        return RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(value));
    }
}
