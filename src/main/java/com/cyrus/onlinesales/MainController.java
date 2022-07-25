package com.cyrus.onlinesales;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/sales")
public class MainController {
	@Autowired
	private OrderRepository orderRepository;

	@PostMapping(path = "/add")
	public @ResponseBody String addOrder(@RequestParam String email, @RequestParam String name) {
		String messageString = "";
		try {
			Order order = new Order();
			order.setEmail(email);
			order.setName(name);
			order.setTimestamp(Timestamp.from(Instant.now()));
			orderRepository.save(order);
			messageString = "order saved";
		} catch (Exception e) {
			// TODO: handle exception
			messageString = "order not saved";
		}
		return messageString;
	}

	@GetMapping(path = "/allorders")
	public @ResponseBody Iterable<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@PutMapping(path = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updateOrder(@RequestParam String email, @RequestParam String name,
			@PathVariable Long id) {
		String messageString = "";
		try {
			Order newOrder = orderRepository.findById(id).get();
			newOrder.setEmail(email);
			newOrder.setName(name);
			newOrder.setTimestamp(Timestamp.from(Instant.now()));
			orderRepository.save(newOrder);
			messageString = "update successfull";
		} catch (Exception e) {
			// TODO: handle exception
			messageString = "update not successfull";
		}
		return messageString;
	}

	@DeleteMapping(path = "/delete/{id}")
	public @ResponseBody String deleteOrder(@PathVariable Long id) {
		String messageString = "";
		try {
			orderRepository.deleteById(id);
			messageString = "deleted successfully";
		} catch (Exception e) {
			// TODO: handle exception
			messageString = "order not deleted";
		}
		return messageString;
	}

}
