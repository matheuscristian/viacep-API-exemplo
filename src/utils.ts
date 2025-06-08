interface ViacepResponse {
  cep: string;
  logradouro: string;
  complemento: string;
  unidade: string;
  bairro: string;
  localidade: string;
  uf: string;
  estado: string;
  regiao: string;
  ibge: string;
  gia: string;
  ddd: string;
  siafi: string;
}

interface ViacepError {
  erro: boolean
}

export async function makeRequest(cep: string) {
  return await fetch(`/api/ws/${cep}/json`)
    .then((res) => {
      if (!res.ok) {
        throw new Error("Couldn't fetch api");
      }

      return res.json();
    })
    .then((data) => {
      return data as ViacepResponse | ViacepError;
    })
    .catch((err) => {
      throw new Error(err);
    });
}
