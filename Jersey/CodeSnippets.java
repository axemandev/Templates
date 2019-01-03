
@Path("/{id}")
public class AccountService {

    @GET
    @Path("/details")
    @Produces(MediaType.APPLICATION_JSON)
    public String getBalance(@PathParam("id") int accountNumber)
            throws JsonGenerationException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = new Account(accountNumber, "Savings");
        account.deposit((int) (Math.random() * 10000));
        String json = mapper.writeValueAsString(account);
        return json;
    }
}
