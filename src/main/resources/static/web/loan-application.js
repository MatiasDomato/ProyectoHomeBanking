var app6 = new Vue({
  el: "#app7",
  data: {
    client: {},
    accounts: {},
    loans: [],
    name: "",
    amount: 0,
    payments: 0,
    numberAccount: "",
    loansHipotecario: {},
    loansAutomovilistico: {},
    loansPersonal: {},
    interes: 0,
    total: 0,
    interesTotal: 0,
    nameAdmin: "",
    amountAdmin: "",
    paymentsAdmin: [],
    interesAdmin: "",
    filterLoans: 0,
    paymentsLoans1: [],
    maxAmountLoans: "",
    interesLoanFilter: 0,
    interesLoan: 0,
  },
  created() {
    this.loadData();
    this.loadLoans();
    this.interesMonto();
  },
    methods: {
    loadData() {
      axios.get("/api/clients/current").then((response) => {
        console.log(response);
        this.client = response.data;
      });
    },
    loadLoans() {
      axios.get("/api/loans").then((response) => {
        console.log(response);
        this.loans = response.data;

        this.loans.sort((a, b) => a.id - b.id);

        this.filterLoans = this.loans.filter((loan) => loan.name == this.name);

        this.paymentsLoans1 = this.filterLoans[0].payments;
        this.maxAmountLoans = this.filterLoans[0].amount;

        this.interesLoanFilter = this.filterLoans[0].interes;
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
    crearPrestamo() {
      axios
        .post("/api/loans", {
          loanType: this.name,
          amount: this.amount,
          payment: this.payments,
          numberAccount: this.numberAccount,
        })
        .then((response) => {
          console.log("Created loan");
          return (window.location.href = "/web/accounts.html");
        })

        .catch((e) => {
          Swal.fire({
            text: "Verifica si completaste correctamente todos los campos",
            icon: "error",
            showConfirmButton: false,
            timer: 3000,
          });
          console.log(e);
        });
    },
    crearPrestamoAdmin() {
      axios
        .post("/api/create/loans", {
          name: this.nameAdmin,
          amount: this.amountAdmin,
          payments: this.paymentsAdmin,
          interes: this.interesAdmin,
        })
        .then((response) => {
          console.log("Created loan");
          return (window.location.href = "/web/accounts.html");
        })

        .catch((e) => {
          Swal.fire({
            text: "Verifica si completaste correctamente todos los campos",
            icon: "error",
            showConfirmButton: false,
            timer: 3000,
          });
          console.log(e);
        });
    },
    interesMonto(parametro) {
      this.interesLoan = this.amount / this.payments;
      this.interesLoan = ((this.interesLoan * parametro) + (this.interesLoan)).toFixed(2)
      this.interesTotal = this.interesLoan * this.payments
    },
  },
});
