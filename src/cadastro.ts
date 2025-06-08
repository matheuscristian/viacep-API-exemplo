import swal from "sweetalert2";
import { makeRequest } from "./utils";

const searchButton = document.getElementById("search-cep-btn");

const cep = document.getElementById("cep") as HTMLInputElement;
if (!cep) {
  throw new Error("No CEP input found");
}

const address = document.getElementById("address") as HTMLInputElement;
if (!address) {
  throw new Error("No address input found");
}

const number = document.getElementById("number") as HTMLInputElement;
if (!number) {
  throw new Error("No number input found");
}

const neighborhood = document.getElementById(
  "neighborhood"
) as HTMLInputElement;
if (!neighborhood) {
  throw new Error("No neighborhood input found");
}

const city = document.getElementById("city") as HTMLInputElement;
if (!city) {
  throw new Error("No city input found");
}

const state = document.getElementById("state") as HTMLSelectElement;
if (!state) {
  throw new Error("No state input found");
}

searchButton?.addEventListener("click", async () => {
  cep.disabled = true;

  if (!cep.value || cep.value.length !== 8) {
    await swal.fire("Erro", "O CEP precisa de exatos 8 números.", "error");
    cep.disabled = false;
    cep.focus();
    return;
  }

  const data = await makeRequest(cep.value).catch(() => null);

  if (!data) {
    await swal.fire("Erro", "Não foi possível buscar o CEP.", "error");
    cep.disabled = false;
    cep.focus();
    return;
  }

  if ("erro" in data) {
    await swal.fire("Erro", "CEP não encontrado.", "error");
    cep.disabled = false;
    cep.focus();
    return;
  }

  address.value = data.logradouro;
  neighborhood.value = data.bairro;
  city.value = data.localidade;
  state.value = data.uf;

  cep.disabled = false;
  number.focus();
});

let canAutoComplete = true;
cep.addEventListener("input", () => {
  if (!searchButton) {
    return;
  }

  if (cep.value.length < 8) {
    canAutoComplete = true;
  }

  if (canAutoComplete && cep.value.length === 8) {
    canAutoComplete = false;
    searchButton.click();
  }
});

document.getElementById("form")?.addEventListener("submit", (e) => {
  if (cep.value && document.activeElement === cep && searchButton) {
    e.preventDefault();
    searchButton.click();
  }
});
