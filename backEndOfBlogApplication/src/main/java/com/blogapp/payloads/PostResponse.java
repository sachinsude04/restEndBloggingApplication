package com.blogapp.payloads;

import java.util.List;

import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostResponse {

	
	private List<PostDto> postDtos;
	private int pageNumber;
	private int pageSize;
	private long totalElement;
	private int totalPages;
	private boolean isLastPage;
	
}
