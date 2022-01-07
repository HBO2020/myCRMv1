import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { LivraisonFrService } from '../service/livraison-fr.service';

import { LivraisonFrComponent } from './livraison-fr.component';

describe('LivraisonFr Management Component', () => {
  let comp: LivraisonFrComponent;
  let fixture: ComponentFixture<LivraisonFrComponent>;
  let service: LivraisonFrService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [LivraisonFrComponent],
    })
      .overrideTemplate(LivraisonFrComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LivraisonFrComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LivraisonFrService);

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
    expect(comp.livraisonFrs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
