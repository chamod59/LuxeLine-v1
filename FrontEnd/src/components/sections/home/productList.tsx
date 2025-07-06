
import { useEffect, useState } from "react";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";

interface Product {
  id: string;
  title: string;
  details: string;
  price: number;
  img: string;
  quantity: number;
}

const Products = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchProducts = async () => {
      setLoading(true);
      setError(null);

      try {
        const res = await fetch("http://localhost:8080/api/v1/product");

        if (!res.ok) {
          throw new Error(`Error: ${res.status} ${res.statusText}`);
        }

        const data: Product[] = await res.json();
        setProducts(data);
      } catch (err: any) {
        setError(err.message || "Failed to fetch products");
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  if (loading)
    return (
      <div className="flex justify-center mt-10 text-xl font-semibold">
        Loading products...
      </div>
    );

  if (error)
    return (
      <div className="flex justify-center mt-10 text-red-600 font-semibold">
        {error}
      </div>
    );

  return (
    <>
      <div className="flex justify-center mt-10">
        <h1 className="text-3xl md:text-4xl font-serif text-center">
          Latest featured Products
        </h1>
      </div>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-10 container my-10">
        {products.length === 0 && (
          <p className="col-span-full text-center text-gray-500">
            No products found.
          </p>
        )}
        {products.map((product) => (
          <div key={product.id} className="col-span-1">
            <Card>
              <div className="flex flex-col items-center border border-gray-400 rounded-lg overflow-hidden">
                <img
                  src={product.img}
                  alt={product.title}
                  className="w-4/6 transition-transform duration-300 ease-in-out transform hover:scale-110"
                />
                <CardHeader>
                  <CardTitle>{product.title}</CardTitle>
                  <CardDescription>{product.details}</CardDescription>
                </CardHeader>
                <CardContent>
                  <p className="font-bold">RS.{product.price.toFixed(2)}</p>
                </CardContent>
                <CardFooter>
                  <div className="flex flex-row gap-5">
                    <Link to={`/product/${product.id}`}>
                      <Button className="bg-green-500 hover:bg-green-700 text-black px-10">
                        Buy Now
                      </Button>
                    </Link>
                    <Button className="bg-yellow-500 hover:bg-yellow-700 text-black px-10">
                      See All --
                    </Button>
                  </div>
                </CardFooter>
              </div>
            </Card>
          </div>
        ))}
      </div>
    </>
  );
};

export default Products;
