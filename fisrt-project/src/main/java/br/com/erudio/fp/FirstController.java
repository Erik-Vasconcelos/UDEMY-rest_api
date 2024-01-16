package br.com.erudio.fp;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

	private static final String TEMPLATE = "Hello, %s";
	private final AtomicLong count = new AtomicLong();

	@GetMapping("/message")
	public Message getMessage(@RequestParam(name = "name", defaultValue = "word") String name) {
		return new Message(count.incrementAndGet(), String.format(TEMPLATE, name));
	}

}
