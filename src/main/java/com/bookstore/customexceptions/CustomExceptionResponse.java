package com.bookstore.customexceptions;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomExceptionResponse {

	private LocalDate timeStamp;
	private String message;
	private String details;
	
}
