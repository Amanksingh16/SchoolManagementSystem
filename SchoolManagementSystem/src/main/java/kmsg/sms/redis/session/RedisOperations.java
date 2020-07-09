package kmsg.sms.redis.session;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public class RedisOperations {

    private RedisTemplate<String,Object> redisTemplate;
    private HashOperations<String,Object,RedisData> hashOperations;
    private ValueOperations<String,Object> valueOperations;

    public RedisOperations(RedisTemplate<String,Object> redisTemplate){
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
        this.valueOperations = this.redisTemplate.opsForValue();
    }
    
    @PostConstruct
    public void clearSessions() {
    	for(RedisData d : findAll())
    		delete(d.getSessionId());
    }

    public void save(RedisData data, String sessionId){  	
        hashOperations.put("SCHOOL", sessionId, data);
    }
    public List<RedisData> findAll(){
        return hashOperations.values("SCHOOL");
    }
    public RedisData findById(String id){
        return (RedisData) hashOperations.get("SCHOOL", id);
    }
    public void delete(String id){
        hashOperations.delete("SCHOOL", id);
    }
    public void putValueWithExpireTime(String key,RedisData value,long timeout,TimeUnit unit) {
        valueOperations.set(key, value, timeout, unit);
    }
    public Object getValue(String key) {
        return valueOperations.get(key);
    }
}
