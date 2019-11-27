function edit(productId) {
    if (productId){
        document.cookie="productId="+productId+"; max-age =;"+1*60*60;
        location.reload("./service_edit.html");
    }
}