package br.com.erudio.cp.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class DozerMapper {

	private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	
//	private static ModelMapper mapper = new ModelMapper();

	public static <O, D> D parseObject(O object, Class<D> destination) {
		return mapper.map(object, destination);
	}

	public static <O, D> List<D> parseListObjects(List<O> objects, Class<D> destination) {
		List<D> destinations = new ArrayList<>();

		for (O o : objects) {
			destinations.add(mapper.map(o, destination));
		}

		return destinations;
	}

}
