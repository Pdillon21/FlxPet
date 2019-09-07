package com.example.fluxpet;

public class PetController {

    public void getAllAvailablePets (final ResultListener<PetContainer> listener){
        PetDAO petDAO = new PetDAO();
        petDAO.getAllAvailablePets(new ResultListener<PetContainer>() {
            @Override
            public void finish(PetContainer result) {
                listener.finish(result);
            }
        });
    }
}
