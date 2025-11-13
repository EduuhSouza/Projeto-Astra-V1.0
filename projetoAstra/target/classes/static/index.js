const container = document.querySelector('.container');
const LoginLink = document.querySelector('.SignInLink');
const RegisterLink = document.querySelector('.SignUpLink');

// FUNÇÃO
RegisterLink.addEventListener('click',()=>{
    container.classList.add('active');
});

LoginLink.addEventListener('click',()=>{
    container.classList.remove('active');
});