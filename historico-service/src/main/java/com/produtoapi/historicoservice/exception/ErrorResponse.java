package com.produtoapi.historicoservice.exception;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class ErrorResponse {

	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String message;
	private String path;


}