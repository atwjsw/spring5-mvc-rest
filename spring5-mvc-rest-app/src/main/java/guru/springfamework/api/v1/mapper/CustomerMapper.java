package guru.springfamework.api.v1.mapper;

//import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controllers.v1.CustomerController;
import guru.springfamework.domain.Customer;
import guru.springfamework.model.CustomerDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

//    @Mapping( target = "customerUrl", expression = "java(CustomerController.BASE_URL + \"/\" + customer.getId())")
    CustomerDTO toDTO(Customer customer);

    Customer toEntity(CustomerDTO customer);

    @AfterMapping
    default void setBookAuthor(@MappingTarget CustomerDTO customerDTO, Customer customer) {
        customerDTO.setCustomerUrl(CustomerController.BASE_URL + "/" + customer.getId());
    }
}
