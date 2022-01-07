import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { FactureVenteService } from '../service/facture-vente.service';

import { FactureVenteComponent } from './facture-vente.component';

describe('FactureVente Management Component', () => {
  let comp: FactureVenteComponent;
  let fixture: ComponentFixture<FactureVenteComponent>;
  let service: FactureVenteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [FactureVenteComponent],
    })
      .overrideTemplate(FactureVenteComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FactureVenteComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FactureVenteService);

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
    expect(comp.factureVentes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
