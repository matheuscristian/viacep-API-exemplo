import swal from "sweetalert2";
import { makeRequest } from "./utils";

const searchButton = document.getElementById(
  "search-cep-btn"
) as HTMLButtonElement;
const cep = document.getElementById("cep") as HTMLInputElement;

const address = document.getElementById("address") as HTMLInputElement;
const number = document.getElementById("number") as HTMLInputElement;
const neighborhood = document.getElementById(
  "neighborhood"
) as HTMLInputElement;
const city = document.getElementById("city") as HTMLInputElement;
const state = document.getElementById("state") as HTMLSelectElement;

if (
  !searchButton ||
  !cep ||
  !address ||
  !number ||
  !neighborhood ||
  !city ||
  !state
) {
  throw new Error("One or more form elements are missing.");
}

async function doSearch() {
  async function showError(desc: string) {
    await swal.fire("Erro!", desc, "error");
    searchButton.disabled = false;
    cep.disabled = false;
    cep.focus();
  }

  cep.disabled = true;
  searchButton.disabled = true;

  const cepRegex = /^[0-9]{8}$/;
  if (!cepRegex.test(cep.value)) {
    await showError("O CEP precisa conter exatamente 8 números.");
    return;
  }

  const data = await makeRequest(cep.value).catch(() => null);

  if (!data) {
    await showError("Não foi possível buscar o CEP.");
    return;
  }

  if ("erro" in data) {
    await showError("CEP não encontrado.");
    return;
  }

  address.value = data.logradouro;
  neighborhood.value = data.bairro;
  city.value = data.localidade;
  state.value = data.uf;

  cep.disabled = false;
  searchButton.disabled = false;
  number.focus();
}

searchButton.addEventListener("click", doSearch);

let canAutoComplete = true;
cep.addEventListener("input", () => {
  if (cep.value.length < 8) {
    canAutoComplete = true;
  }

  if (canAutoComplete && cep.value.length === 8) {
    canAutoComplete = false;
    doSearch();
  }
});

cep.addEventListener("keydown", (e) => {
  if (e.key === "Enter" && cep.value && document.activeElement === cep) {
    e.preventDefault();
    doSearch();
    return;
  }
});

document.getElementById("form")?.addEventListener("submit", (e) => {
  e.preventDefault();

  const form = e.target as HTMLFormElement;

  const nameInput = form.elements.namedItem("name") as HTMLInputElement;
  const emailInput = form.elements.namedItem("email") as HTMLInputElement;
  const passwordInput = form.elements.namedItem("password") as HTMLInputElement;
  const cepInput = form.elements.namedItem("cep") as HTMLInputElement;
  const addressInput = form.elements.namedItem("address") as HTMLInputElement;
  const numberInput = form.elements.namedItem("number") as HTMLInputElement;
  const neighborhoodInput = form.elements.namedItem(
    "neighborhood"
  ) as HTMLInputElement;
  const cityInput = form.elements.namedItem("city") as HTMLInputElement;
  const stateInput = form.elements.namedItem("state") as HTMLSelectElement;

  const data = {
    nome: nameInput.value,
    email: emailInput.value,
    senha: passwordInput.value,
    cep: cepInput.value,
    endereco: addressInput.value,
    numero: numberInput.value,
    bairro: neighborhoodInput.value,
    cidade: cityInput.value,
    estado: stateInput.value,
  };

  fetch("http://localhost:8080/api/funcionarios", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  })
    .then((response) => {
      if (response.ok) {
        swal.fire("Sucesso!", "Cadastro feito com sucesso.", "success");
        form.reset();
      } else {
        swal.fire("Erro!", "Não foi possível realizar o cadastro.", "error");
      }
    })
    .catch((error) => {
      console.error("Erro:", error);
      swal.fire("Erro!", "Erro de rede ou servidor.", "error");
    });
});
