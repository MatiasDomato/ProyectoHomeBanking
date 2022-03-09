var register = new Vue({
  el: "#register",
  data: {
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  },
  methods: {
    register() {
      if(this.firstName != '' && this.lastName != '' && this.email.includes("@") != '' && this.password != ''){
        Swal.fire({
          icon: 'success',
          title: 'Te registraste correctamente!',
          showConfirmButton: false,
          timer: 2000
        })
        setTimeout(() => {
          axios
          .post(
            "/api/clients",
            `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`,
            { headers: { "content-type": "application/x-www-form-urlencoded" } }
          )
          .then((response) => {
              console.log("registered");
              
            this.singIn()

            })
  
          .catch((e) => {
            console.log(e);
          });
        }, 2100);
      } else {
        Swal.fire({
          text: 'Por favor completa los datos anteriores correctamente',
          icon: 'error',
          showConfirmButton: false,
          timer: 3000
        })
      }
        
      
    },
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
              console.log(e);
            });
        
      } else {
        Swal.fire({
          text: 'Por favor completa los datos anteriores correctamente',
          icon: 'error',
          showConfirmButton: false,
          timer: 3000
        })
      }
    },
  },
});
