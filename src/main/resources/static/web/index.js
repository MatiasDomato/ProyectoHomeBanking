var app = new Vue({
  el: "#app",
  data: {
    email: "",
    password: "",
  },
  methods: {
    singIn() {
      if (this.email != "" && this.password != "") {
          axios
            .post(
              "/api/login",
              `email=${this.email}&password=${this.password}`,
              {
                headers: {
                  "content-type": "application/x-www-form-urlencoded",
                },
              }
            )
            .then((response) => {
              console.log("signed in!!!");
              return (window.location.href = "/web/accounts.html");
            })

            .catch((e) => {
              Swal.fire({
                text: 'Email o Contraseña Inválido',
                icon: 'error',
                showConfirmButton: false,
                timer: 3000
              })
              console.log(e);
            });
  
      }  
      
    },
  },
});
