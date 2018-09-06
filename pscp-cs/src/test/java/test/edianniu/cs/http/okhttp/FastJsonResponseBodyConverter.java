/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月2日 下午12:11:29 
 * @version V1.0
 */
package test.edianniu.cs.http.okhttp;

import java.io.IOException;
import java.lang.reflect.Type;

import com.alibaba.fastjson.JSON;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import okio.BufferedSource;
import okio.Okio;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月2日 下午12:11:29 
 * @version V1.0
 */
public class FastJsonResponseBodyConverter <T> implements Converter<ResponseBody, T> {
	private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
	 private final Type type;

	    public FastJsonResponseBodyConverter(Type type) {
	        this.type = type;
	    }

	    /*
	     * 转换方法
	     */
	     @Override
	     public T convert(ResponseBody value) throws IOException  {
	         BufferedSource bufferedSource = Okio.buffer(value.source());
	         String tempStr = bufferedSource.readUtf8();
	         bufferedSource.close();
	         return JSON.parseObject(tempStr, type);

	     }
}
