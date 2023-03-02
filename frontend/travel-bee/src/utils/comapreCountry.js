function compareCountry(a, b) {
  if (a.country < b.country) {
    return -1;
  }
  if (a.country > b.country) {
    return 1;
  }
  return 0;
}

export default compareCountry
