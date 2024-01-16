package com.inheritence.type.demo.fetch_types;

import com.inheritence.type.demo.fetch_types.model.Category;
import com.inheritence.type.demo.fetch_types.model.Product;
import com.inheritence.type.demo.fetch_types.repository.CategoryRepository;
import com.inheritence.type.demo.fetch_types.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Ashwani Kumar
 * Created on 16/01/24.
 */
@SpringBootTest
public class FetchTypeTests {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    static final String PRODUCT_LOG_PREFIX = "$$$$$$$$$$$$- ";
    static final String CATEGORY_LOG_PREFIX = "###########- ";


    /**
     * <h2>Note:</h2> Hibernate runs 3 queries to insert one record in the table.
     * 2 queries to maintain the sequence for rowId and 1 query for record insertions.
     * <p>
     * In the save2Category case, a total of 6 queries will be executed as follows:
     * <br/><br/><b>Total Queries:</b><br/>
     * <br/>1. {@code select next_val as id_val from category_seq for update}
     * <br/>2. {@code update category_seq set next_val= ? where next_val=?}
     * <br/>3. {@code select next_val as id_val from category_seq for update}
     * <br/>4. {@code update category_seq set next_val= ? where next_val=?}
     * <br/>5. {@code insert into category (name,id) values (?,?)}
     * <br/>6. {@code insert into category (name,id) values (?,?)}
     */
    //@Test
    void saveCategoriesTest(){
        List<Category> categories = new ArrayList<>();
        Category c1 = new Category();
        c1.setName("Phone");
        Category c2 = new Category();
        c2.setName("Laptop");
        categories.add(c1);
        categories.add(c2);
        List<Category> savedCategories = categoryRepository.saveAll(categories);
        System.out.println(CATEGORY_LOG_PREFIX+"Categories saved: "+savedCategories);
    }


    /**
     * <br/><br/><b>Total Queries:</b><br/>
     * <br/>1. {@code select c1_0.id,c1_0.name from category c1_0 where c1_0.id=?}
     * <br/>2. {@code select next_val as id_val from product_seq for update}
     * <br/>3. {@code update product_seq set next_val= ? where next_val=?}
     * <br/>4. {@code insert into product (category_id,name,price,id) values (?,?,?,?)}
     */
   // @Test
    void saveProductWithExistCategoryTest(){
        Product product = new Product();
        product.setName("Samsung FE");
        product.setPrice(50000d);
        Optional<Category> categoryOptional = categoryRepository.findById(1L); // categoryId 1 exist in db
        categoryOptional.ifPresent(product::setCategory);
        Product savedProduct = productRepository.save(product);
        System.out.println(PRODUCT_LOG_PREFIX+"Product saved: "+savedProduct);

    }

    /**
     * <h2>Note:</h2> Instead of 4 queries Only 3 queries will be executed successfully:
     * <p>
     * <br/><br/><b>Total Queries:</b><br/>
     * <br/>1. {@code select c1_0.id, c1_0.name from category c1_0 where c1_0.id=?}
     * <br/>2. {@code select next_val as id_val from product_seq for update}
     * <br/>3. {@code update product_seq set next_val= ? where next_val=?}
     * <br/>
     * <br/>
     * However, while executing the insert query for the product, Hibernate will first check
     * the Category in the database for the mapping with this new Product. If it is not found in the database,
     * an exception will be thrown:
     * <br/><br/>
     * {@code
     * Caused by: org.hibernate.TransientPropertyValueException: object references an unsaved transient instance -
     * save the transient instance before flushing : com.inheritence.type.demo.fetch_types.model.Product.category ->
     * com.inheritence.type.demo.fetch_types.model.Category
     * 	at org.hibernate.engine.spi.CascadingActions$8.noCascade(CascadingActions.java:372)
     * 	at org.hibernate.engine.internal.Cascade.cascade(Cascade.java:173)
     * }
     */
   // @Test
    void saveProductWithNonExistCategoryTest(){
        Product product = new Product();
        product.setName("gel Pen");
        product.setPrice(10d);
        Optional<Category> categoryOptional = categoryRepository.findById(3L);// categoryId 3 not exist in db
        if (categoryOptional.isEmpty()){
            Category category = new Category();
            category.setName("Pen"); // adding new category as Pen with gel Pen product
            product.setCategory(category); // hibernate will throw error when insert product. See above doc.
        }else{
            product.setCategory(categoryOptional.get());
        }
        Product savedProduct = productRepository.save(product);
        System.out.println(PRODUCT_LOG_PREFIX+"Product saved with new category: "+savedProduct);

    }

    /**
     * <h2>Note:</h2> For primitive type and Object type , the default Fetch type is : EAGER
     * <p>
     * <br/><br/><b>Total Queries: 1 query for product fetch and N query for N categoryIds returned with product fetch</b><br/>
     * <br/> Suppose there are four category - [Phone, Laptop, Pen, Kitchen]
     * <br/> And 100 product with these four categories only then 1 query to fetch all 100 product
     * <br/> and then hibernate collect categories Ids. for these 4 ids , 4 query for category table will be execute
     * <br/> For above example, queries execute as below -
     * <br/>1. {@code select p1_0.id,p1_0.category_id,p1_0.name,p1_0.price from product p1_0}
     * <br/>2. {@code select c1_0.id,c1_0.name from category c1_0 where c1_0.id=?}
     * <br/>3. {@code select c1_0.id,c1_0.name from category c1_0 where c1_0.id=?}
     * <br/>4. {@code select c1_0.id,c1_0.name from category c1_0 where c1_0.id=?}
     * <br/>5. {@code select c1_0.id,c1_0.name from category c1_0 where c1_0.id=?}
     * <br/><br/>
     *
     */
  // @Test
    void getAllProductsTest() {
        System.out.println(PRODUCT_LOG_PREFIX+"getProductsTest starts........");

        List<Product> products = productRepository.findAll();
        System.out.println("Product\tCategory");
        products.forEach(product -> {
            Category category = product.getCategory();
            System.out.println(product.getName() + "\t" + category.getName());
        });
        System.out.println(PRODUCT_LOG_PREFIX+"getProductsTest ends........");

    }

    /**
     * <h2>Note:</h2> For primitive type and Object type , the default Fetch type is : EAGER
     * <p>
     * <br/><br/><b>Total Queries:</b><br/>
     * <br/>1. {@code select p1_0.id,c1_0.id,c1_0.name,p1_0.name,p1_0.price from product p1_0 left join category c1_0 on c1_0.id=p1_0.category_id where p1_0.id=?}
     * <br/><br/>
     * After this product with category will be printed. Only one join query will be exeucted for prodID=1
     */
   // @Test
    void getSingleProductTest() {
        System.out.println(PRODUCT_LOG_PREFIX+"getProductTest starts........");
        Long prodId = 1L;
        Optional<Product> productOptional = productRepository.findById(prodId);
        if (productOptional.isEmpty())
            System.err.println(PRODUCT_LOG_PREFIX+"Product not found for id: " + prodId);
        else {
            Product product = productOptional.get();
            Category category = product.getCategory();
            System.out.println("Product\tCategory");
            System.out.println(product.getName() + "\t" + category.getName());
        }
        System.out.println(PRODUCT_LOG_PREFIX+"getProductTest ends........");

    }

    /**
     * <h2>Note:</h2> For primitive types and Object types, the default fetch type is <b>EAGER</b> but for Collection type is <b>LAZY</b>.
     * <p>
     * Use the <b>@Transactional</b> annotation for testing LAZY fetch type:
     * After <b>findById</b>, the Hibernate session will be closed.
     * When <b>category.getProducts()</b> is called, it attempts to fetch all products due to the default LAZY fetch type.
     * ORM will throw a <b>LazyInitializationException</b> because we are trying to access a lazily-loaded collection
     * outside of the Hibernate session.
     * <br/><br/><b>Total Queries:</b><br/>
     * <br/>1. {@code select c1_0.id, c1_0.name from category c1_0 where c1_0.id=?}
     * <br/>2. {@code select p1_0.category_id, p1_0.id, p1_0.name, p1_0.price from product p1_0 where p1_0.category_id=?}
     * <br/><br/>
     */
    @Transactional
    @Test
    void getCategoryWithAllProductsTest_DefaultFetchType_LAZY_FOR_Collection() {
        Optional<Category> categoryOptional = categoryRepository.findById(1L);// 1 query from cat table for catId=1
        Category category = categoryOptional.get();
        List<Product> products = category.getProducts(); // 1 query from product table where catid=1
        System.out.println(products);
    }

    //  ,
    /**
     * <h2>Note:</h2> For making collection <b>EAGER</b>  type,  add fetchType=EAGER in Category class then execute this Test
     * <p>
     * No need of  <b>@Transactional</b> annotation for EAGER type testing now. because no need to maintain session.
     * <br/><br/><b>Total Queries: Only 1 join query</b><br/>
     * <br/>1. {@code select c1_0.id,c1_0.name,p1_0.category_id,p1_0.id,p1_0.name,p1_0.price from category c1_0 left join product p1_0 on c1_0.id=p1_0.category_id where c1_0.id=?}
     * <br/><br/>
     */
    @Test
    void getCategoryWithAllProductsTest_DefaultFetchType_EAGER_FOR_Collection() {
        Optional<Category> categoryOptional = categoryRepository.findById(1L);
        Category category = categoryOptional.get();
        List<Product> products = category.getProducts();
        System.out.println(products);
    }

}
