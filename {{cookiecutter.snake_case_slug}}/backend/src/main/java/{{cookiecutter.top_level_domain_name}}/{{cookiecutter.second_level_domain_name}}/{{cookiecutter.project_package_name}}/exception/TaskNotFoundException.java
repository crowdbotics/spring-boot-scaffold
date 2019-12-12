/*
 * Copyright 2019 crowdbotics.com
 */

package com.crowdbotics.sample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <h1>Task Not Found Exception</h1>
 *
 * @author crowdbotics.com
 */
@ResponseStatus(
	reason="Task not found."
	, value = HttpStatus.NOT_FOUND
)
public class TaskNotFoundException
	extends RuntimeException
{
}
