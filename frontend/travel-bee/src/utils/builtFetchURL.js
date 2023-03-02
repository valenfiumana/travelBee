function buildFetchURL(productsURL, categoryId) {
    let fetchURL;
    categoryId === 1 ? fetchURL = `${productsURL}/random` : fetchURL = `${productsURL}/category/${categoryId}`;
    return fetchURL
}

export default buildFetchURL
