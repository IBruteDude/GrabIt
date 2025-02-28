package com.grabit.error.exceptions;

import java.util.UUID;

public class EntityNotFoundException extends BaseException {
	
	public EntityNotFoundException(String entityName, UUID id) {
		super("error.entity_not_found", entityName, id);
	}

}
