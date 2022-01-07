import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { CommandeClientService } from '../service/commande-client.service';

import { CommandeClientComponent } from './commande-client.component';

describe('CommandeClient Management Component', () => {
  let comp: CommandeClientComponent;
  let fixture: ComponentFixture<CommandeClientComponent>;
  let service: CommandeClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CommandeClientComponent],
    })
      .overrideTemplate(CommandeClientComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CommandeClientComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CommandeClientService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.commandeClients?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
