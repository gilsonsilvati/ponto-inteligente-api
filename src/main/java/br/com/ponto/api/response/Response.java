package br.com.ponto.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Response<T> {

	private T data;
	private List<String> errors = getErrors() == null ? new ArrayList<>() : getErrors();

}
