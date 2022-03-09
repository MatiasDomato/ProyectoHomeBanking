var app3 = new Vue({
  el: "#app3",
  data: {
    client: {},
    accounts: {},
    transactions: {},
  },
  created() {
    this.loadData();
  },
  methods: {
    loadData() {
      const urlParams = new URLSearchParams(window.location.search);
      console.log(window.location.search);
      const id = urlParams.get("id");

      axios.get(`/api/accounts/${id}`).then((response) => {
        console.log(response);
        this.accounts = response.data;
        this.transactions = response.data.transactions;

        this.transactions.sort((a, b) => a.id - b.id);
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
  },
  
});
