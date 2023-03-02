import Search from "./Search/Search";
import Categories from "./Categories/Categories";
import ProductGrid from "./ProductGrid/ProductGrid";
import './Main.css';

function Main() {
    return (
        <main>
            <Search />
            <Categories />
            <ProductGrid />
        </main>
    )
}

export default Main
