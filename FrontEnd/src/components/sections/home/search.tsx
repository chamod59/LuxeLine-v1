import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { useEffect, useState } from "react";
import api from "@/context/appContext";
import AddProductCard from "../products/addProductCard";
import { useAuth } from "@/context/authContext";

const Search = () => {
  const [username, setUsername] = useState("");
  const { isLoggedIn } = useAuth();
  const [showAddProductForm, setShowAddProductForm] = useState(false);

  const openAddProductForm = () => {
    if (!isLoggedIn) {
      alert("You have to log into application first");
      return;
    }
    setShowAddProductForm(true);
  };
  const closeAddProductForm = () => setShowAddProductForm(false);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) return;

    const fetchUsername = async () => {
      try {
        const res = await api.get(`/username`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        setUsername(res.data);
      } catch (err) {
        console.error("Failed to fetch username", err);
      }
    };

    fetchUsername();
  }, []);

  return (
    <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4 m-6">
      {/* Hidden on small screens, visible on medium and large screens */}
      <div className="hidden md:flex md:basis-1/4">
        <p className="text-center mt-2">
          Hello <b>{username || "Guest"}</b>
        </p>
      </div>

      {/* Hidden on small screens, visible on medium and large screens */}
      <div className="hidden md:flex md:basis-1/2">
        <div className="flex w-full space-x-2">
          <Input type="text" placeholder="Search your desire" />
          <DropdownMenu>
            <DropdownMenuTrigger className="border-black border p-1 px-6 rounded-full">
              <p>Categories</p>
            </DropdownMenuTrigger>
            <DropdownMenuContent>
              <DropdownMenuLabel>Categories</DropdownMenuLabel>
              <DropdownMenuSeparator />
              <DropdownMenuItem>T-shirts</DropdownMenuItem>
              <DropdownMenuItem>Pants</DropdownMenuItem>
              <DropdownMenuItem>Shoes</DropdownMenuItem>
              <DropdownMenuItem>Sunglasses</DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </div>
      </div>

      {/* Hidden on medium and large screens, visible on small screens */}
      <div className="md:hidden flex flex-row gap-2 w-full">
        <DropdownMenu>
          <DropdownMenuTrigger className="bg-purple-300 hover:bg-purple-500 flex-grow px-4 rounded-full text-black font-semibold">
            <p>Categories</p>
          </DropdownMenuTrigger>
          <DropdownMenuContent className="bg-gray-100">
            <DropdownMenuLabel>Categories</DropdownMenuLabel>
            <DropdownMenuSeparator />
            <DropdownMenuItem>T-shirts</DropdownMenuItem>
            <DropdownMenuItem>Pants</DropdownMenuItem>
            <DropdownMenuItem>Shoes</DropdownMenuItem>
            <DropdownMenuItem>Sunglasses</DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
        <Button
          type="submit"
          className="bg-purple-300 hover:bg-purple-500 flex-grow px-4 rounded-full text-black font-semibold"
        >
          Advanced
        </Button>
      </div>

      <div className="hidden md:flex md:basis-1/4 md:justify-end space-x-2">
        <Button type="submit" className="rounded-full px-6" variant="outline">
          Search
        </Button>
        <Button
          type="button"
          className="rounded-full px-6"
          variant="outline"
          onClick={openAddProductForm}
        >
          Add Product
        </Button>
      </div>

      {showAddProductForm && (
        <div className="fixed inset-0 bg-black bg-opacity-30 flex justify-center items-start p-4 z-50 overflow-auto">
          <div className="bg-white rounded-md shadow-md max-w-xl w-full relative">
            <button
              onClick={closeAddProductForm}
              className="absolute top-2 right-2 text-gray-600 hover:text-gray-900 text-xl font-bold"
              aria-label="Close Add Product Form"
            >
              &times;
            </button>
            <AddProductCard />
          </div>
        </div>
      )}
    </div>
  );
};

export default Search;
