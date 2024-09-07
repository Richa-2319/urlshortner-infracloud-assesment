package com.infracloud.shorturl.service.strategy;

import com.infracloud.shorturl.constant.Constants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("hash")
public class HashStrategy implements ShortnerStrategy{
    @Override
    public String generateShortUrl(String longUrl) {
        String uuid = java.util.UUID.randomUUID().toString();
        StringBuilder result = new StringBuilder();
        String cleanUUID = uuid.replace("-","");
        for(char ch:cleanUUID.toCharArray()){
            result.append(Constants.BASE62CHARS.charAt(ch%62));
        }

        return result.substring(0,8);
    }
}
