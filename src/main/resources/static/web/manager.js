var app = new Vue({
  el: "#app",
  data: {
    clients: [],
    json: [],
    newClient: {},
    firstName: "",
    lastName: "",
    email: "",
  },
  created() {
    this.loadData();
  },
  methods: {
    //Me carga los clientes
    loadData() {
      axios
        .get("rest/clients")

        .then((response) => {
          this.json = response.data;
          this.clients = response.data._embedded.clients;
        })
        .catch((e) => {
          console.log(e);
        });
    },

    //Voy agredando los clientes y uso un this para enviarlo al servidor java

    addClient() {
      if (this.firstName && this.lastName && this.email.included("@")) {
        let client = {
          firstName: this.firstName,
          lastName: this.lastName,
          email: this.email,
        };
        alert("Se agrego el cliente con exito"), this.postClient(client);
      }
    },

    //Hago el llamado a la funcion y indico donde alojarse los datos

    postClient(client) {
      axios.post("rest/clients", client).then((response) => {
        this.loadData();
      });
    },
    eliminar: function (arg1) {
      axios
        .delete(arg1)
        .then(
          (response) => alert("Se elimino el cliente con exito"),
          console.log(arg1),
          this.loadData(),
          location.reload()
        );
    },
  },
});
