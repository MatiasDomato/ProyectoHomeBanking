var app5 = new Vue({
  el: "#app5",
  data: {
    client:{},
    cards: {},
    cardType: "",
    cardColor: "",
    debitCards:[],
    creditCards:[],
    debitFilter:[],
    creditFilter:[]
  },
  created() {
    this.loadData();
  },
  methods: {
    loadData: function () {
      axios.get("/api/clients/current").then((response) => {
        this.client = response.data;
        this.cards = response.data.cards;
       
        this.debitCards = this.cards.filter(card => card.type == "DEBIT")
        this.creditCards = this.cards.filter(card => card.type == "CREDIT")
     
        this.debitFilter = this.debitCards.filter(card => card.active == true)
        this.creditFilter = this.creditCards.filter(card => card.active == true)

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
          return (window.location.href = "/web/index.html");
        })

        .catch((e) => {
          console.log(e);
        });
    },
    crearNuevaTarjeta() {
      axios
        .post(
          "/api/clients/current/cards",
          `cardType=${this.cardType}&cardColor=${this.cardColor}`,
          {
            headers: { "content-type": "application/x-www-form-urlencoded" },
          }
        )
        .then((response) => {
          console.log("Created cards");
          return (window.location.href = "/web/cards.html");
        })

        .catch((e) => {
          console.log(e);
        });
    },
  },
});
