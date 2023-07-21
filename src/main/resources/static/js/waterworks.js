const imageInput = document.getElementById('imageInput');
const imagePreview = document.getElementById('imagePreview');
imageInput.onchange = evt => {
    const [file] = imageInput.files
    if (file) {
        imagePreview.src = URL.createObjectURL(file)
    }
}