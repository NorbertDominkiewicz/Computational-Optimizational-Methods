package com.ndominkiewicz.backend.result;

import com.ndominkiewicz.backend.model.Result;

public record UnexpectedResult(String message) implements Result {
}
