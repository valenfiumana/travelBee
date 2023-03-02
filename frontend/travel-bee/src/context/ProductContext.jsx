import { createContext, useState } from "react";

const ProductContext = createContext();

function ProductContextProvider ({children}) {
    const [ categoryActiveIndex, setCategoryActiveIndex ] = useState(1);
    const [ selectedCategory, setSelectedCategory ] = useState(1);
    const [ producs, setProducts ] = useState();
    const [ range, setRange ] = useState();

    return (
        <ProductContext.Provider value={{
            products: [producs, setProducts],
            selectedCategory: [selectedCategory, setSelectedCategory],
            categoryActiveIndex: [categoryActiveIndex, setCategoryActiveIndex],
            range: [range, setRange] }} >
            {children}
        </ProductContext.Provider>
    )
}

export { ProductContext, ProductContextProvider }
