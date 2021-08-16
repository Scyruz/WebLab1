/**
 * 
 */
package mx.tec.web.lab.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mx.tec.web.lab.manager.ProductManager;
import mx.tec.web.lab.vo.Product;

/**
 * @author Scyruz
 *
 */

/**
 * Controls REST call methods
 */
@RestController
public class ProductController 
{
	@Resource
	private ProductManager productManager;
	
	/**
	 * REST Method to call all products available
	 * @return 200 if successful
	 */
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts() 
	{
		List<Product> products = productManager.getProducts();
		ResponseEntity<List<Product>> responseEntity = new ResponseEntity<>(products, HttpStatus.OK);
		
		return responseEntity;
	}
	
	/**
	 * REST Method to call information from specific product
	 * @param id
	 * @return 200 if successful else 204
	 */
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable(value = "id") String id) 
	{
	ResponseEntity<Product> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
	
	Optional<Product> product = productManager.getProduct(id);
	
	if (product.isPresent()) 
		{
		responseEntity = new ResponseEntity<>(product.get(), HttpStatus.OK);
		}
	
	return responseEntity;
	}
	
	/**
	 * REST Method to create new product
	 * @param newProduct
	 * @return 201 if successful else 400
	 */
	@PostMapping("/products")
	public ResponseEntity<Product> addProduct(@RequestBody Product newProduct) 
	{
		ResponseEntity<Product> responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Optional<Product> product = productManager.addProduct(newProduct);
		
		if (product.isPresent()) 
			{
			responseEntity = new ResponseEntity<>(product.get(), HttpStatus.CREATED);
			}
		
		return responseEntity;

	}
	
	/**
	 * REST Method to update a product's information
	 * @param id
	 * @param newProduct
	 * @return 201 if successful else 400 
	 */
	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") String id, @RequestBody Product newProduct) {
		ResponseEntity<Product> responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Optional<Product> product = productManager.getProduct(id);
				
		if (product.isPresent()) {
			Optional<Product> updatedProduct = productManager.updateProduct(product.get(), newProduct);
			responseEntity = new ResponseEntity<>(updatedProduct.get(), HttpStatus.CREATED);
		}
		
		return responseEntity;
	}
	
	/**
	 * REST Method to delete a product
	 * @param id
	 * @return 200 if successful else 204 
	 */
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable(value = "id") String id) {
		ResponseEntity<Product> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		Optional<Product> product = productManager.getProduct(id);
		
		if (product.isPresent()) {
			productManager.deleteProduct(product.get());
			responseEntity = new ResponseEntity<>(HttpStatus.OK);
		}
		
		return responseEntity;
	}
}
