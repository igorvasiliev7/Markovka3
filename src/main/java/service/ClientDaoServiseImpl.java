//package service;
//
//import dao.factory.DaoFactory;
//import model.Call;
//import model.Client;
//import model.ClientDTO;
//import model.Visit;
//
//import java.util.LinkedList;
//import java.util.List;
//
//public class ClientDaoServiseImpl {
//
// private List<ClientDTO> clientDTOList = new LinkedList<>();
//
//   public List<ClientDTO> findAll(){
//      List<Client> clients = DaoFactory.getClientDao().findAll();
//      List<Call> calls = DaoFactory.getCallDao().findAll();
//   //   List<Visit> visits = DaoFactory.getVisitDao().findAll();
//
//       for (Client client : clients) {
//           ClientDTO clientDTO = new ClientDTO(client.getId(),client.getName(),client.getPhone(), client.getStatus(), client.getCard());
//           clientDTOList.add(clientDTO);
//       }
//
//       for (int i = 0; i < calls.size(); i++) {
//           for (int i1 = 0; i1 < clientDTOList.size(); i1++) {
//               if (calls.get(i).getId()==clientDTOList.get(i1).getClientId()&&clientDTOList.get(i1).getLastCallDate().isEmpty()){
//                clientDTOList.get(i1).setLastCallDate(calls.get(i).getDate());
//                break;
//               }
//           }
//       }
//
////       for (int i = 0; i < visits.size(); i++) {
////           for (int i1 = 0; i1 < clientDTOList.size(); i1++) {
////               if (visits.get(i).getId()==clientDTOList.get(i1).getClientId()&&clientDTOList.get(i1).getLastCallDate().isEmpty()){
////                   clientDTOList.get(i1).setLastCallDate(calls.get(i).getDate());
////                   break;
////               }
////           }
////       }
//    return clientDTOList;
//   }
//}
