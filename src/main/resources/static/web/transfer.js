var app6 = new Vue({
  el: "#app6",
  data: {
    client: {},
    accounts: [],
    amount: '',
    description: '',
    cuentaOrigen:'',
    cuentaDestino:'',
    cuentaDestinatario:'',
  

    
  },
  created() {
    this.loadData();
  },
  methods: {
    loadData() {
      
      axios.get("/api/clients/current/accounts")
      .then((response) => {
        console.log(response);
        this.client = response.data;
        this.accounts = response.data;


      });
      axios.get("/api/clients/current").then((response) => {
        console.log(response);
        this.client = response.data;
        

      });
    },
    singOut() {
      axios
        .post("/api/logout")
        .then((response) => console.log("signed out!!!"))

        .then((response) => {
          console.log("signed in!!!");
          return (window.location.href = "/web/home.html");
        })

        .catch((e) => {
          console.log(e);
        });
    },
    crearTransferencia() {
      axios
        .post(
          "/api/transactions",
          `amount=${this.amount}&description=${this.description}&sourceAccount=${this.cuentaOrigen}&destinationAccountNumber=${this.cuentaDestino}`
        )
        .then((response) => {
          console.log("Created transactions");
          return (window.location.href = "/web/accounts.html");
        })

        .catch((e) => {
          console.log(e);
          Swal.fire({
            text: 'Por favor completa los datos anteriores correctamente',
            icon: 'error',
            showConfirmButton: false,
            timer: 3000
          });
        });
        
    },
  },
});
