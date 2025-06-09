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
    await swal.fire("Erro", desc, "error");
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

document.getElementById("form")?.addEventListener("submit", (e) => {
  if (cep.value && document.activeElement === cep) {
    e.preventDefault();
    doSearch();
  }
});
