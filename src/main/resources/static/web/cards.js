let date = new Date()
let dateNow = date.getFullYear() + '/' + String(date.getMonth() + 1).padStart(2, '0') + '/' + String(date.getDate()).padStart(2, '0');

var app4 = new Vue({
  el: "#app4",
  data: {
    client: {},
    accounts: {},
    cards: [],
    debitCards:[],
    creditCards:[],
    debitFilter:[],
    creditFilter:[],
    vencimiento: dateNow,

  },
  created() {
    this.loadData();
  },
  methods: {
    loadData: function () {
      axios.get("/api/clients/current").then((response) => {
        
        this.client = response.data;
        this.accounts = response.data.accounts;
        this.cards = response.data.cards;

        this.debitCards = this.cards.filter(card => card.type == "DEBIT")
        this.creditCards = this.cards.filter(card => card.type == "CREDIT")

        this.debitFilter = this.debitCards.filter(card => card.active == true)
        this.creditFilter = this.creditCards.filter(card => card.active == true)

        this.botonCards = response.data.cards;

        if (this.debitFilter.length >= 3 && this.creditFilter.length >=3) {
          this.botonCards = false
        }

        

      });
    },
    dates(cardThruDate) {
      let monthYear = cardThruDate.slice(5, 7) + "/" + cardThruDate.slice(2, 4);
      return monthYear;
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
    crearNuevaTarjeta() {
      axios
        .post("/api/clients/current/cards",  {
          headers: { "content-type": "application/x-www-form-urlencoded" },
        })
        .then((response) => {
          console.log("Created cards");
          return (window.location.href = "/web/cards.html");
        })
  
        .catch((e) => {
          console.log(e);
        });
    },
    eliminarTarjeta(id){
      axios.patch("/api/clients/current/cards/delete/" + id)
      .then((response) => {
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Se elimino la tarjeta correctamente',
          showConfirmButton: false,
          timer: 3000
        })
        setTimeout(() => {
          (window.location.reload());
        }, 2200);
        console.log("Se elimino la tarjeta");
        
      })

    }
  },
});
