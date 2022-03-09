var app2 = new Vue({
  el: "#app2",
  data: {
    client: {},
    accounts: {},
    loans: {},
    botonCuenta: true,
    accountsFilter:[],
    accountsFilterLength:[],
    accountType:""
  },
  created() {
    this.loadData();
  },
  methods: {
    loadData: function () {
      axios.get("/api/clients/current").then((response) => {
        console.log(response.data);
        this.client = response.data;
        this.accounts = response.data.accounts;
        this.loans = response.data.loans;

        console.log(this.loans);

        this.accounts.sort((a, b) => a.id - b.id);
        this.loans.sort((a, b) => a.id - b.id);

        this.botonCuenta = response.data.accounts;

        this.accountsFilter = this.accounts.filter(account => account.active == true)
        this.accountsFilterLength = this.accountsFilter.length

        if (this.botonCuenta.length >= 3) {
          this.botonCuenta = false;
        }

        

      });
    },
    singOut() {
      axios
        .post("/api/logout")
        .then((response) => console.log("signed out!!!"))

        .then((response) => {
          console.log("signed out!!!");
          return (window.location.href = "/web/home.html");
        })

        .catch((e) => {
          console.log(e);
        });
    },
    crearNuevaCuenta(accountType) {
      axios
        .post("/api/clients/current/accounts",`accountType=${accountType}`, {
          headers: { "content-type": "application/x-www-form-urlencoded" },
        })
        .then((response) => {
          console.log("Created Account");
          return (window.location.href = "/web/accounts.html");
        })

        .catch((e) => {
          console.log(e);
        });
    },
    eliminarCuenta(id){
      axios.patch("/api/clients/current/accounts/delete/" + id)
      .then((response) => {
                
       (window.location.reload());
        
        console.log("Se elimino la cuenta");
        
      })

    }
  },
});
