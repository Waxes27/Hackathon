package za.co.wethinkcode.hackathon.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.wethinkcode.hackathon.Models.Hackathon;

@Repository
public interface HackathonRepository extends JpaRepository<Hackathon,Long> {
}
