
/**
 * 제품의 이름과 가격을 가지고 있는 클래스 (POJO)
 */
public class Product {
    
    private String name;
    private int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return new StringBuffer().append("name : ")
                .append(this.name)
                .append(", price : ")
                .append(this.price)
                .toString();
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
