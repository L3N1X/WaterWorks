//AJAX
document.addEventListener("DOMContentLoaded", function (){
   let imageElements = document.querySelectorAll('.item-image');
   imageElements.forEach(function (imageElement){
       let itemId = imageElement.getAttribute('data-item-id');
       fetch('/api/image/' + itemId)
           .then((response) => {
               if(!response.ok) {
                   throw new Error('Network error while loading the image')
               }
               return response.text();
           })
           .then((base64image) => {
               if(base64image.length > 100)
                    imageElement.src = 'data:image/png;base64,' + base64image;
           })
           .catch((error) => {
               console.error('Fetch error ', error);
           });
   });
});

const imageInput = document.getElementById('imageInput');
const imagePreview = document.getElementById('imagePreview');
if(imageInput !== null) {
    imageInput.onchange = evt => {
        const [file] = imageInput.files
        if (file) {
            imagePreview.src = URL.createObjectURL(file)
        }
    }
}
