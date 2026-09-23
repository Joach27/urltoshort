package com.joach27.urltoshort.generator;

import java.util.List;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.sqids.Sqids;

import com.joach27.urltoshort.exception.SlugNotFoundException;


@Service 
public class SlugGenerator {

    private final Sqids sqids;

    public SlugGenerator(@Value("${app.sqids.alphabet}") String alphabet){
        this.sqids = Sqids.builder()
				.alphabet(alphabet)
				.minLength(3)
				.build();
    }


	public String encodeId(Long id)	{
	    if (id == null || id <= 0){
			throw new IllegalArgumentException("Id must be positive integer");
		}
	    return sqids.encode(Collections.singletonList(id));
	}

	public Long decodeSlug(String slug){
        List<Long> numbers = sqids.decode(slug);
        if (numbers.isEmpty()){
            throw new SlugNotFoundException("Invalid URL");
        }
        return numbers.get(0);
	}

}