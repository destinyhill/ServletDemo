package httpSession;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name="ShoppingCartServlet",urlPatterns={"/products","/viewProductDetails","/addToCart","/viewCart"})
public class ShoppingCartServlet extends HttpServlet {
	private static final long serialVersionUID=-20L;
	private static final String CART_ATTRIBUTE="cart";
	
	private List<Product> products=new ArrayList<Product>();
	private NumberFormat currencyFormat=NumberFormat.getCurrencyInstance(Locale.US);
	//初始化四种产品包括其细节
	@Override
	public void init() throws ServletException {
		products.add(new Product(1,"TV","LOW-cost from China",159.95F));
		products.add(new Product(2,"Player","High quality stylish player",99.95F));
		products.add(new Product(3,"System","5 speaker hifi system with ipod",129.95F));
		products.add(new Product(4,"iPod player","can paly multiple formats",39.95F));
	}

	
	        //URL都要调用doGet方法，依据URL的后缀启动不同方法，产生不同页面
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String uri=req.getRequestURI();
		if(uri.endsWith("/products")){
			sendProductList(resp);
		}else if(uri.endsWith("/viewProductDetails")){
			sendProductDetails(req,resp);
		}else if(uri.endsWith("/viewCart")){
			showCart(req,resp);
		}
		
	}
	
	//当点击buy时，则获取其请求，将产品及其数量放入购物车
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int quantity=0;
		int productId=0;
		try{
			productId=Integer.parseInt(req.getParameter("id"));
			quantity=Integer.parseInt(req.getParameter("quantity"));
		}catch(NumberFormatException e){}
		
		
		Product product=getProduct(productId);
		if(product!=null&&quantity>0){
			ShoppingItem shoppingItem=new ShoppingItem(product, quantity);
			HttpSession session=req.getSession();
			List<ShoppingItem> cart=(List<ShoppingItem>) session.getAttribute(CART_ATTRIBUTE);
			if(cart==null){
				cart=new ArrayList<ShoppingItem>();
				session.setAttribute(CART_ATTRIBUTE, cart);
			}
			cart.add(shoppingItem);
		}
		//若产品为空，则启动产品列表
		sendProductList(resp);
	}

	
	        //发送产品的列表
	private void sendProductList(HttpServletResponse response) throws IOException{
		response.setContentType("text/html");
		PrintWriter write=response.getWriter();
		write.println("<html><head><title>Products</title>"+
		"</head><body><h2>Products</h2>");
		write.println("<ul>");
		//列出product列表
		for(Product product:products){
			write.println("<li>"+product.getName()+"("+currencyFormat.format(product.getPrice())
					+") ("+"<a href='viewProductDetails?id="+product.getId()+"'>Details</a>)");
		}
		write.println("</ul>");
		write.println("<a href='viewCart'>View Cart</a>");
		write.println("</body></html>");
				
	}
	private Product getProduct(int productId) {
		for(Product product:products){
			if(product.getId()==productId){
				return product;
			}
		}
		return null;
	}

	
	        //发送产品描述
	private void sendProductDetails(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		PrintWriter write=resp.getWriter();
		int productId=0;
		try{
			productId=Integer.parseInt(req.getParameter("id"));
			
		}catch(NumberFormatException e){}
		
		Product product =getProduct(productId);
		
		if(product!=null){
			write.println("<html><head>"
					+"<title>Product Details</title></head>"
					+"<body><h2>Product Details</h2>"
					+"<form method='post' action='addToCart'>");
			write.println("<input type='hidden' name='id' "
					+"value=' "+productId+" '/>");
			write.println("<table>");
			write.println("<tr><td>Name:</td><td>"
					+product.getName()+"</td></tr>");
			write.println("<tr><td>Description:</td><td>"
					+product.getDescription()+"</td></tr>");
			write.println("<tr>"+"<tr>"
					+"<td><input name='quantity' ></td>"
					+"<td><input type='submit' value='buy'></td>"
					+"</tr>");
			write.println("<tr><td colspan='2'>"
					+"<a href='products'>Product List</a>"
					+"</td></tr>");
			write.println("</table>");
			write.println("</form></body>");
			
		}else{
			write.println("No product found");
		}		
	}
   

	
	       //展示购物车
	private void showCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	     resp.setContentType("text/html");
	     PrintWriter writer=resp.getWriter();
	     writer.println("<html><head><title>Shopping Cart</title></head>");
	     writer.println("<body><a href='products'>Product List</a>");
	     HttpSession session=req.getSession();
	     List<ShoppingItem> cart=(List<ShoppingItem>)session.getAttribute(CART_ATTRIBUTE);
	     if(cart!=null){
	    	 writer.println("<table>");
	    	 writer.println("<tr>"
	    	 		 +"<td style='width:150px'>Quantity</td>"
	    			 +"<td style='width:150px'>Product</td> "
	    			 +"<td style='width:150px'>Price</td>"
	    			 +"<td>Amount</td></tr>");
	    	 double total=0.0;
	    	 for(ShoppingItem shoppingItem:cart){
	    		 Product product=shoppingItem.getProduct();
	    		 int quantity=shoppingItem.getQuantity();
	    		 if(quantity!=0){
	    			 float price=product.getPrice();
	    			 writer.println("<tr>"
	    					 +"<td>"+quantity+"</td>"
	    					 +"<td>"+product.getName()+"</td>"
	    					 +"<td>"+currencyFormat.format(price)+"</td>");
	    			 double subtotal=price*quantity;
	    			 writer.println("<td>"+currencyFormat.format(subtotal));
	    			 total+=subtotal;
	    			 writer.println("</tr>");
	    		 }
	    	 }
	    	 writer.println("<tr><td colspan='4'>"
	    			 +"style='text-align:right'>"
	    			 +"Total:"
	    			 +currencyFormat.format(total)
	    			 +"</td></tr>");
	    	 writer.println("</table>");
	    	 
	     }
		writer.println("</table></body></html>");
	}
	
	
 
}
